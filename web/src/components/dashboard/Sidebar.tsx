import { Link, useLocation } from "react-router-dom";
import { Home, MessageSquare } from "lucide-react";

const Sidebar: React.FC = () => {
  const location = useLocation();

  const isActive = (path: string): boolean => location.pathname === path;

  return (
    <div className="flex flex-col w-64 bg-card border-r border-border">
      <div className="flex items-center justify-center h-16 border-b border-border">
        <span className="text-2xl font-semibold text-primary">MedChat</span>
      </div>
      <nav className="flex-1 overflow-y-auto">
        <ul className="p-2 space-y-1">
          <li>
            <Link
              to="/home"
              className={`flex items-center p-2 text-foreground rounded-lg ${
                isActive("/") ? "bg-accent" : "hover:bg-accent/50"
              }`}
            >
              <Home className="w-5 h-5 mr-3" />
              Dashboard
            </Link>
          </li>
          {/* <li>
            <Link
              to="/patients"
              className={`flex items-center p-2 text-foreground rounded-lg ${
                isActive("/patients") ? "bg-accent" : "hover:bg-accent/50"
              }`}
            >
              <Users className="w-5 h-5 mr-3" />
              Patients
            </Link>
          </li> */}
          <li>
            <Link
              to="/chat"
              className={`flex items-center p-2 text-foreground rounded-lg ${
                isActive("/chat") ? "bg-accent" : "hover:bg-accent/50"
              }`}
            >
              <MessageSquare className="w-5 h-5 mr-3" />
              Chat
            </Link>
          </li>
          {/* <li>
            <Link
              to="/settings"
              className={`flex items-center p-2 text-foreground rounded-lg ${
                isActive("/settings") ? "bg-accent" : "hover:bg-accent/50"
              }`}
            >
              <Settings className="w-5 h-5 mr-3" />
              Settings
            </Link>
          </li> */}
        </ul>
      </nav>
    </div>
  );
};

export default Sidebar;
