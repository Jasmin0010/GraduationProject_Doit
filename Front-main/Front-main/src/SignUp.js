import { Button, Container, Grid, TextField, Typography } from '@mui/material';
import React from 'react';
import { signup } from './service/ApiService';
import { useNavigate } from 'react-router-dom';

function SignUp() {
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const email = data.get("email");
        const password = data.get("password");
        const username = data.get("username");
        // const weight = data.get("weight"); // 별도 모달로 구현
        // const height = data.get("height");

        if (data.get("password") !== data.get("password-check")) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }
        console.log(email, password, username);
        // ApiService의 signin 메서드를 사용 해 로그인.
        signup({ email: email, username: username, password: password }).then(() => {
            alert("회원가입 성공");
            window.location.href = "/login";
        }).catch((error) => {
            if (error.json) error.json().then((json) => {
                alert(json.error);
            })
        });
    }

    return (
        <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>

            <form noValidate onSubmit={handleSubmit}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            id="email"
                            label="이메일 주소"
                            name="email"
                            autoComplete="email"
                        />
                    </Grid>

                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            name="username"
                            label="사용자 이름"
                            id="username"
                            autoComplete="username"
                        />
                    </Grid>

                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            name="password"
                            label="패스워드"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            name="password-check"
                            label="패스워드 확인"
                            type="password"
                            id="password-check"
                            autoComplete="current-password"
                        />
                    </Grid>

                    {/* <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="weight"
                                label="체중"
                                id="weight"
                                autoComplete="weight"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                name="height"
                                label="키"
                                id="height"
                                autoComplete="height"
                            />
                        </Grid> */}

                    <Grid item xs={12}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                        >
                            회원가입
                        </Button>
                    </Grid>
                </Grid>
            </form>
            <div style={{ marginTop: "8%", marginBottom: "20px" }}>

                <Button
                    color='inherit'
                    variant='contained'
                    onClick={() => {
                        navigate('/login')
                    }}
                    style={{
                        flexGrow: 0,
                        flexShrink: 0,
                    }}
                    fullWidth
                >
                    돌아가기
                </Button>
            </div>
        </Container>
    );
}

export default SignUp;