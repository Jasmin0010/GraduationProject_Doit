import { API_BASE_URL } from "../app-config";
const ACCESS_TOKEN = "ACCESS_TOKEN";
const USER_ID = "USER_ID";

export async function call(api, method, request) {
  let headers = new Headers({
    "Content-Type": "application/json",
  });

  // 로컬 스토리지에서 ACCESS TOKEN 가져오기
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  if (accessToken && accessToken !== null) {
    headers.append("Authorization", "Bearer " + accessToken);
  }

  let options = {
    headers: headers,
    url: API_BASE_URL + api,
    method: method,
  };

  if (request) {
    // GET method
    options.body = JSON.stringify(request);
  }
  return fetch(options.url, options)
    .then((response) => {
      if (!response.ok) {
        return Promise.reject(response);
      }
      return response.json();
    })
    .catch((error) => {
      // 추가된 부분
      console.log(error.status);
      if (error.status === 403) {
        window.location.href = "/login"; // redirect
      }
      return Promise.reject(error);
    });
}

export async function signin(userDTO) {
  return call("/auth/signin", "POST", userDTO).then((response) => {
    if (response.token) {
      // 로컬 스토리지에 토큰 저장
      localStorage.setItem(ACCESS_TOKEN, response.token);
      localStorage.setItem(USER_ID, response.id);
      // token이 존재하는 경우 Todo 화면으로 리디렉트
      window.location.href = "/";
    }
  });
}

export async function signup(userDTO) {
  return call("/auth/signup", "POST", userDTO).then((response) => {
    if (response.token) {
      // 로컬 스토리지에 토큰 저장
      localStorage.setItem(ACCESS_TOKEN, response.token);
      localStorage.setItem(USER_ID, response.id);
      // token이 존재하는 경우 Todo 화면으로 리디렉트
      window.location.href = "/";
    }
  });
}

export async function signout() {
  localStorage.removeItem(ACCESS_TOKEN);
  localStorage.removeItem(USER_ID);
  window.location.href = "/login";
}

export function getUserId() {
  const accessToken = localStorage.getItem(ACCESS_TOKEN);
  if (!accessToken) {
    return null;
  }
  return localStorage.getItem(USER_ID);
}