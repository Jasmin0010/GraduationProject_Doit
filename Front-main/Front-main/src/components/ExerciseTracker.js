import React, { useState } from 'react';
import { Container, Grid, Paper, Typography, TextField, Button } from '@mui/material';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

const daysOfWeek = [ 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday','Sunday'];

const ExerciseTracker = () => {
    const [exerciseTimes, setExerciseTimes] = useState(new Array(7).fill('')); // 각 요일의 운동 기록을 저장하는 배열
    const [dailyReviews, setDailyReviews] = useState(new Array(7).fill('')); // 각 요일의 데일리 리뷰를 저장하는 배열
    const [selectedTodos, setSelectedTodos] = useState(null); // 선택된 요일의 투두 리스트
  
    const today = new Date().getDay(); // 오늘의 요일을 구합니다.
  
    const handleInputChange = (event, index) => {
      const newExerciseTimes = [...exerciseTimes];
      newExerciseTimes[index] = event.target.value;
      setExerciseTimes(newExerciseTimes);
    };
  
    const handleAddTime = (dayIndex) => {
      // 운동 기록을 추가하는 함수 (현재 예시에서는 사용하지 않음)
      console.log(`Exercise time for ${daysOfWeek[dayIndex]}: ${exerciseTimes[dayIndex]}`);
    };
  
    const handleReviewChange = (event, index) => {
      const newDailyReviews = [...dailyReviews];
      newDailyReviews[index] = event.target.value;
      setDailyReviews(newDailyReviews);
    };
  
    const handleBarClick = (data, index) => {
      const clickedDay = data.day;
      console.log(`Clicked day: ${clickedDay}`);
      // 여기에 선택된 요일의 투두 리스트를 가져와서 selectedTodos 상태에 설정하는 로직을 추가합니다.
      // 예를 들어, 서버에서 해당 요일의 투두 리스트를 가져와서 설정할 수 있습니다.
      setSelectedTodos(/* 서버에서 가져온 투두 리스트 */);
    };
  
    // 주간 운동 기록을 합산하여 차트 데이터를 생성합니다.
    const chartData = daysOfWeek.map((day, index) => ({
      day,
      time: parseFloat(exerciseTimes[index]) || 0, // 입력된 운동 시간 또는 0으로 설정합니다.
    }));
  
    return (
      <Container>
        <Typography variant="h5" align="center" gutterBottom>
          Daily Exercise Tracker
        </Typography>
        <Grid container spacing={2}>
          {daysOfWeek.map((day, index) => (
            <Grid item xs={15} key={index} style={{ display: index === today ? 'block' : 'none' }}>
              {/* 오늘의 요일만 보이도록 조건부 스타일링을 적용합니다. */}
              <Paper style={{ padding: 16 }}>
                <Grid container spacing={3} alignItems="center">
                  <Grid item xs={9}>
                    <Typography variant="h6" gutterBottom>{day}</Typography>
                    <TextField
                      label="Enter time (minutes)"
                      type="number"
                      value={exerciseTimes[index]}
                      onChange={(event) => handleInputChange(event, index)}
                      fullWidth
                      InputLabelProps={{
                        shrink: true,
                      }}
                    />
                    <TextField
                      label="Daily review"
                      value={dailyReviews[index]}
                      onChange={(event) => handleReviewChange(event, index)}
                      fullWidth
                      multiline
                      rows={4}
                      variant="outlined"
                      style={{ marginTop: '8px' }}
                    />
                  </Grid>
                  <Grid item xs={3}>
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={() => handleAddTime(index)}
                      disabled={!exerciseTimes[index]}
                      fullWidth
                      style={{ height: '100%' }}
                    >
                      Add Time
                    </Button>
                  </Grid>
                </Grid>
              </Paper>
            </Grid>
          ))}
        </Grid>
        <Typography variant="h5" align="center" gutterBottom style={{ marginTop: '32px', marginBottom: '16px' }}>
          Weekly Exercise Statistics
        </Typography>
        <ResponsiveContainer width="100%" height={400}>
          <BarChart data={chartData} onClick={handleBarClick}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="day" interval={0} angle={-45} textAnchor="end" height={60} />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="time" fill="#8884d8" name="Exercise Time (minutes)" />
          </BarChart>
        </ResponsiveContainer>
        {/* 선택된 요일의 투두 리스트를 출력하는 부분 */}
        {selectedTodos && (
          <div style={{ marginTop: '32px' }}>
            <Typography variant="h5" align="center" gutterBottom>
              Selected Day's Todos
            </Typography>
            {/* 여기에 선택된 요일의 투두 리스트를 보여주는 컴포넌트를 추가합니다. */}
          </div>
        )}
      </Container>
    );
  };
  

export default ExerciseTracker;
