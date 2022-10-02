import SignIn from "./Auth/SignIn";
import SignUp from "./Auth/SignUp";
import Header from "./Navbar/Header";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";
import Home from "./Home/Home";



export default function Index(props) {
    const { isLoggedIn, setLoggedIn } = props
    console.log(isLoggedIn);    
    return (
        <div>
            <Header isLoggedIn={isLoggedIn} />
            <BrowserRouter>
                { isLoggedIn ? 
                    <Routes>
                        <Route path="/home" element={<Home setIsLoggedIn={setLoggedIn} /> }>
                        </Route>
                    </Routes>
                    :                                       
                    <Routes>
                        <Route path="/" element={<SignIn setIsLoggedIn={setLoggedIn} isLoggedIn={isLoggedIn} />}>
                        </Route>                                                                
                        <Route path="/signup" element={<SignUp setIsLoggedIn={setLoggedIn} />}>
                        </Route>
                    </Routes>
                } 
            </BrowserRouter>
            { /* <BrowserRouter>
                {isLoggedIn ?
                    <Routes>
                        <Route path="/video" element={<VideoList setLoggedIn={setLoggedIn}/>}>
                        </Route>
                        <Route path="/video/:id" element={<Video setLoggedIn={setLoggedIn}/>}>
                        </Route>
                    </Routes>
                    :
                    <Routes>
                        <Route path="/" element={<SignIn setIsLoggedIn={setLoggedIn} isLoggedIn={isLoggedIn} />}>
                        </Route>
                        <Route path="/signup" element={<SignUp setIsLoggedIn={setLoggedIn} />}>
                        </Route>
                    </Routes>
                }
            </BrowserRouter> */}
        </div>

    )
}