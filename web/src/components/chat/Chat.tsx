import React, { useState, useEffect, useRef } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import Cookies from "js-cookie";

interface Message {
  id: string;
  sender: "doctor" | "patient";
  content: string;
  timestamp: Date;
}

interface Patient {
  id: string;
  name: string;
  lastMessage: string;
}

const ChatPage: React.FC = () => {
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const [inputMessage, setInputMessage] = useState<string>("");
  const [selectedPatient, setSelectedPatient] = useState<Patient | null>(null);

  const [patients, setPatients] = useState<Patient[]>([
    { id: "1", name: "John Doe", lastMessage: "How are you feeling today?" },
    { id: "2", name: "Jane Smith", lastMessage: "Thank you, doctor." },
    {
      id: "3",
      name: "Bob Johnson",
      lastMessage: "When is my next appointment?",
    },
  ]);
  const [email, setEmail] = useState<string>(
    JSON.parse(Cookies.get("user") || "")?.email || ""
  );

  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const newSocket = new WebSocket("wss://api.pjci.in:10095"); // Replace with your WebSocket server URL
    setSocket(newSocket);

    newSocket.onopen = () => {
      const email = JSON.parse(Cookies.get("user") || "")?.email;
      if (email) {
        // Send the "setmyId" action after the connection is established
        newSocket.send(JSON.stringify({ action: "setmyId", customId: email }));
      }
    };

    newSocket.onmessage = (event) => {
      const message = JSON.parse(event.data);
      if (message && message.content && message.sender) {
        setMessages((prevMessages) => [...prevMessages, message]);
      }
    };

    return () => {
      newSocket.close();
    };
  }, []);

  useEffect(() => {
    setEmail(JSON.parse(Cookies.get("user") || "")?.email || "");
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const sendMessage = () => {
    if (socket && inputMessage && selectedPatient) {
      const message: Message = {
        id: email,
        sender: "doctor",
        content: inputMessage,
        timestamp: new Date(),
      };

      socket.send(
        JSON.stringify({
          action: "sendMessageToClient",
          clientId: selectedPatient.id,
          message,
        })
      );

      setMessages((prevMessages) => [...prevMessages, message]);
      setInputMessage("");
    }
  };

  return (
    <div className="flex h-full">
      <Card className="w-1/4 h-full rounded-none border-r">
        <CardHeader>
          <CardTitle onClick={() => setPatients([])}>Patients</CardTitle>
        </CardHeader>
        <CardContent>
          <ScrollArea className="h-[calc(100vh-10rem)]">
            {patients.map((patient) => (
              <div
                key={patient.id}
                className={`flex items-center space-x-4 p-2 hover:bg-accent cursor-pointer ${
                  selectedPatient?.id === patient.id ? "bg-accent" : ""
                }`}
                onClick={() => setSelectedPatient(patient)}
              >
                <Avatar>
                  <AvatarImage
                    src={`/placeholder-avatar-${patient.id}.jpg`}
                    alt={patient.name}
                  />
                  <AvatarFallback>
                    {patient.name
                      .split(" ")
                      .map((n) => n[0])
                      .join("")}
                  </AvatarFallback>
                </Avatar>
                <div>
                  <p className="font-medium">{patient.name}</p>
                  <p className="text-sm text-muted-foreground">
                    {patient.lastMessage}
                  </p>
                </div>
              </div>
            ))}
          </ScrollArea>
        </CardContent>
      </Card>
      <Card className="flex-1 h-full rounded-none">
        <CardHeader>
          <CardTitle>
            {selectedPatient ? selectedPatient.name : "Select a patient"}
          </CardTitle>
        </CardHeader>
        <CardContent className="flex flex-col h-[calc(100vh-10rem)]">
          <ScrollArea className="flex-1 mb-4">
            {messages.map((message) => (
              <div
                key={message.id}
                className={`mb-2 ${
                  message.sender === "doctor" ? "text-right" : "text-left"
                }`}
              >
                <div
                  className={`inline-block p-2 rounded-lg ${
                    message.sender === "doctor"
                      ? "bg-primary text-primary-foreground"
                      : "bg-muted"
                  }`}
                >
                  {message.content}
                </div>
                <div className="text-xs text-muted-foreground mt-1">
                  {new Date(message.timestamp).toLocaleTimeString()}
                </div>
              </div>
            ))}
            <div ref={messagesEndRef} />
          </ScrollArea>
          <div className="flex space-x-2">
            <Input
              type="text"
              placeholder="Type your message..."
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyPress={(e) => e.key === "Enter" && sendMessage()}
            />
            <Button onClick={sendMessage}>Send</Button>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default ChatPage;
