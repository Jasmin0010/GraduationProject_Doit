import React from "react";
import { signin } from "./service/ApiService";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import { Container } from "@material-ui/core";
import { useNavigate } from "react-router-dom";

function Login(props) {
  const navigate = useNavigate();


  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.target);
    const email = data.get("email");
    const password = data.get("password");
    signin({ email: email, password: password }).catch((error) => {
      console.error("로그인 실패", error);
      if(error.json) {
        error.json().then((data) => {
          alert(data.error);
        });
      }
    });
  };

  return (
    <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <Typography component="h1" variant="h5">
            로그인
          </Typography>
        </Grid>
      </Grid>
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
              name="password"
              label="패스워드"
              type="password"
              id="password"
              autoComplete="current-password"
            />
          </Grid>
          <Grid item xs={12}>
            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
            >
              로그인
            </Button>
          </Grid>
        </Grid>
      </form>

      <div style={{ marginTop: "8%", marginBottom: "20px" }}>
        <Button
          variant="contained"
          color="inherit"
          onClick={() => {
            navigate('/signup');
          }}
          fullWidth
        >
          회원가입
        </Button>
      </div>
    </Container>
  );
}

export default Login;
