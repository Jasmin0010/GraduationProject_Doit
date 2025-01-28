import React from 'react';
import { useNavigate } from 'react-router-dom';
import { signout } from '../service/ApiService';

function NavBar({ isLoggedIn, onLogin, onLogout }) {
    // const isLoggedIn = false;
    const navigate = useNavigate();

    return (
        <nav style={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            padding: '0 20px',
            margin: '0',
            height: '60px',
            // boxShadow: '0 2px 5px rgba(0, 0, 0, 0.26)',
            backgroundColor: 'white'
        
        }}>
            <div className="logo">
                {/* Add your logo here */}
            </div>
            <div className="title">
                Do it!
            </div>
            <div className="login-logout">
                {isLoggedIn ? (
                    <button onClick={() => {
                        console.log('Logout')
                        // navigate('/login')
                        signout();
                    }}>Logout</button>
                ) : (
                    <button onClick={() => {
                        console.log('Login')
                        navigate('/login')
                    }}>Login</button>
                )}
            </div>
        </nav>
    );
}

export default NavBar;