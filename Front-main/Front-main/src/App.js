import React, { useState } from "react";
import Todo from "./Todo";
import AddTodo from "./components/AddTodo.js";
import ExerciseTracker from "./components/ExerciseTracker.js";
import YouTubeThumbnail from './components/YouTubeThumbnail.js';
import { Paper, List, Container } from "@material-ui/core";
import { call, getUserId } from "./service/ApiService";
import { useEffect } from "react";
import NavBar from './components/NavBar';
import TodoSection from "./components/TodoSection.js";
import { Box, Grid, Typography } from "@mui/material";
import Statistics from "./components/Statistics.js";
import moment from "moment";
import GoalSection from "./components/GoalSection.js";
import WorkoutFeedback from "./components/WorkoutFeedback.js";
import DatePickerSection from "./components/DatePickerSection.js";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { LocalizationProvider } from "@mui/x-date-pickers";
import ChangeUserInfo from "./components/ChangeUserInfo.js";

const App = () => {
  const userId = getUserId();

  const [workSheetDate, setWorkSheetDate] = useState(new Date());
  const [trackerKey, setTrackerKey] = useState(0);
  const [statKey, setStatKey] = useState(0);

  const [userData, setUserDate] = useState({}); // { username: '', weight: '', height: '' }
  const [todoItems, setTodoItems] = useState([]);
  const [workSheet, setWorkSheet] = useState(null);
  const [workoutVideos, setWorkoutVideos] = useState([]); // [ {title: 'title', video_id: 'url'}, ...]

  useEffect(() => {
    fetchUserInfo();
    fetchWorkoutVideos();
  }, []);

  useEffect(() => {
    fetchTodos(workSheetDate);
    fetchWorkSheet(workSheetDate);
    setStatKey(prev => prev + 1);
    setTrackerKey(prev => prev + 1);
  }, [workSheetDate]);

  const fetchUserInfo = () => {
    call("/auth/me", "GET", null).then((response) => {
      console.log("update user info", response.data);
      setUserDate(response.data);
    });
  }
  const updateUserInfo = (data) => {
    call("/auth/update", "PUT", data).then((response) => {
      fetchUserInfo();
    });
  }

  const fetchTodos = (date) => {
    call(`/todo/by-date?date=${moment(date).format('YYYY-MM-DD')}`, "GET", null).then((response) => {
      console.log("fetchTodos", response.data);
      setTodoItems(response.data)
    });
  }
  const addTodos = (items) => {
    Promise.all(items.map((item) => {
      return call("/todo", "POST", item);
    })).then((response) => {
      fetchTodos(workSheetDate);
    });

  };
  const deleteItem = (item) => {
    call("/todo", "DELETE", item).then((response) =>
      fetchTodos(workSheetDate)
    );
  };
  const update = (item) => {
    call("/todo", "PUT", item).then((response) => {
      fetchTodos(workSheetDate);
    });
  };


  const fetchWorkSheet = (date) => {
    call(`/worksheet/by-date?date=${moment(date).format('YYYY-MM-DD')}`, "GET", null).then((response) => {
      setWorkSheet(response.data);
    });
  }
  // Worksheet 변경
  const changeWorkSheet = (worksheetDTO) => {
    console.log("changeWorkSheet", worksheetDTO);
    call("/worksheet", "PUT", worksheetDTO).then((response) => {
      // setWorkSheet(response.data)
      console.log(response)
      fetchWorkSheet(workSheetDate);
      setStatKey(prev => prev + 1);
    });
  };

  const fetchWorkoutVideos = () => {
    call("/workoutvideo", "GET", null).then((response) => {
      setWorkoutVideos(response.data);
    });
  }

  const handleAddVideo = (videoId) => {
    call("/workoutvideo", "POST", {
      videoId: videoId,
      platform: "youtube"
    }).then((response) => {
      // setWorkoutVideos(response.data);
      fetchWorkoutVideos();
    });
  }
  const handleDeleteVideo = (id) => {
    call(`/workoutvideo/${id}`, "DELETE", null).then((response) => {
      // setWorkoutVideos(response.data);
      fetchWorkoutVideos();
    });
  }


  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <div className='wrapper'>
        <NavBar isLoggedIn={!!userId} onLogin={() => {
          console.log('Login')
        }} onLogout={() => {
          console.log('Logout')
        }} />

        <div className="App">
          <Grid container spacing={2}>

            <Grid item xs={6} md={8}>
              <GoalSection goalText={workSheet?.title || ""} onChange={(goalText) => {
                changeWorkSheet({ ...workSheet, title: goalText })
              }} />
            </Grid>
            <Grid item xs={6} md={4}>
              <Box display={"flex"} justifyContent={"space-between"} alignItems={"center"} paddingInline={2}>
                <div>
                  <Typography gutterBottom>{userData.username} {userData.height ? `${userData.height}cm` : ""} {userData.weight ? `${userData.weight}kg` : ""}</Typography>
                </div>
                <ChangeUserInfo userData={userData} updateUserInfo={updateUserInfo} />
              </Box>
              <DatePickerSection date={workSheetDate} setDate={setWorkSheetDate} />
            </Grid>

            <Grid item xs={6} md={8}>
              <TodoSection date={workSheetDate} add={addTodos} todoItems={todoItems} deleteItem={deleteItem} updateItem={update} />
            </Grid>
            <Grid item xs={6} md={4}>
              <YouTubeThumbnail workoutVideos={workoutVideos} addNewVideo={handleAddVideo} removeVideo={handleDeleteVideo} />
            </Grid>

            <Grid item xs={6} md={8}>
              <WorkoutFeedback key={trackerKey} workSheet={workSheet} onChange={(data) => {
                changeWorkSheet({
                  "id": workSheet.id,
                  "userId": workSheet.userId,
                  "title": workSheet.title,
                  "content": data.content,
                  "date": workSheet.date,
                  "timeSpent": data.timeSpent,
                  "status": workSheet.status,
                  "created": workSheet.created,
                  "updated": workSheet.updated
                })
              }} />
            </Grid>
            <Grid item xs={6} md={4}>
              <Statistics key={statKey} />
            </Grid>
          </Grid>

        </div>
      </div>
    </LocalizationProvider>
  );
}

export default App;
