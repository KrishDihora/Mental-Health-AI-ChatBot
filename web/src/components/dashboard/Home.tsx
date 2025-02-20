import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

const Home: React.FC = () => {
  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold mb-4">Welcome, Dr. Jane Doe</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Pending Messages</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold">3</p>
          </CardContent>
        </Card>
        <Card>
          <CardHeader>
            <CardTitle>Total Patients</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-3xl font-bold">28</p>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Home;
