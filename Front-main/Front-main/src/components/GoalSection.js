import React, { useEffect, useState } from 'react';
import { TextField, Paper, Button, Container, Box, Typography } from '@mui/material';

const GoalSection = ({ goalText, onChange }) => {
    const [title, setTitle] = useState('');

    useEffect(() => {
        setTitle(goalText);
    }, [goalText]);

    const onInputChange = (e) => {
        setTitle(e.target.value);
    };

    const onButtonClick = () => {
        onChange(title);
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            onButtonClick();
        }
    };

    return (
        <Container>
        <Paper style={{ padding: 16 }}>
            <Box display={"flex"} alignItems={"center"}>
                <Typography variant={"h6"} gutterBottom >
                    목표
                </Typography>
                <TextField
                    placeholder='Count'
                    type='text'
                    onChange={onInputChange}
                    value={title}
                    onKeyDown={handleKeyDown}
                    fullWidth
                    style={{
                        width: 100,
                        marginLeft: 16,
                        marginRight: 16,
                        flexGrow: 1,
                        flexShrink: 1,
                    }}
                />
                <Button
                    color='secondary'
                    variant='outlined'
                    onClick={onButtonClick}
                    style={{
                        height: 56,
                        flexGrow: 0,
                        flexShrink: 0,
                    }}
                >
                    변경
                </Button>
            </Box>
        </Paper>
        </Container>
    );
};

export default GoalSection;
