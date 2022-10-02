import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"

const Home = () => {
    let navigate = useNavigate();
    const [authenticated, setauthenticated] = useState(null);
    useEffect(() => {
        const loggedInUser = localStorage.getItem('authenticated');
        if (loggedInUser) {
            setauthenticated(loggedInUser);
        }
    }, []);
    if (!authenticated) {
        navigate("/");
    } else {
        return (
            <div>
              <p>Welcome to your Dashboard</p>
            </div>
        );
    }

};
export default Home;