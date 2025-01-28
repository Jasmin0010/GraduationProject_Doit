import React, { useEffect } from 'react';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';

function ChangeUserInfo({ userData, updateUserInfo }) {
    // State variables for username, weight, and height
    const [username, setUsername] = React.useState('');
    const [weight, setWeight] = React.useState('');
    const [height, setHeight] = React.useState('');
    const [open, setOpen] = React.useState(false);

    useEffect(() => {
        if (userData) {
            setUsername(userData.username);
            setWeight(userData.weight);
            setHeight(userData.height);
        }
    }, [userData]);

    // Function to handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();

        console.log('Updated user info:', username, weight, height);

        updateUserInfo({
            username: username,
            weight: weight,
            height: height
        });
        setOpen(false);
    };

    return (
        <div>
            <Button
                variant="contained"
                color='primary'
                onClick={() => setOpen(true)}>변경</Button>

            {/* Modal component */}
            <Modal open={open} onClose={() => setOpen(false)}>
                <div style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '100vh'
                }}>
                    <Box sx={{ width: 300, bgcolor: 'background.paper', p: 4, borderRadius: 2 }}>
                        <h2>Change User Info</h2>
                        <form onSubmit={handleSubmit} style={{
                            display: 'flex',
                            flexDirection: 'column',
                            gap: '10px'
                        }}>
                            <TextField
                                label="Username"
                                placeholder='Username'
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                            <TextField
                                label="Weight"
                                placeholder='kg'
                                value={weight}
                                onChange={(e) => setWeight(e.target.value)}
                            />
                            <TextField
                                label="Height"
                                placeholder='cm'
                                value={height}
                                onChange={(e) => setHeight(e.target.value)}
                            />
                            <Button type="submit">Save</Button>
                        </form>
                    </Box>
                </div>
            </Modal>
        </div>
    );
};

export default ChangeUserInfo;