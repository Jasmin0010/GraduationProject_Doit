import React, { useEffect, useState } from 'react';
import { Container, Grid, Paper, Typography, TextField, Button, Box } from '@mui/material';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import moment from 'moment';


const WorkoutFeedback = ({ workSheet, onChange }) => {
  const [exerciseTime, setExerciseTime] = useState(0);
  const [dailyReview, setDailyReview] = useState('');


  useEffect(() => {
    if (!workSheet) {
      setExerciseTime(0);
      setDailyReview('');
      return;
    }
    setExerciseTime(workSheet.timeSpent);
    setDailyReview(workSheet.content);
  }, [workSheet]);

  const handleSave = () => {
    // 운동 기록과 데일리 리뷰를 저장하는 함수
    onChange({ timeSpent: exerciseTime, content: dailyReview });
  }

  if (!workSheet) return null;

  return (
    <Container style={{ marginBlock: 16 }}>
      <Typography variant="h5" align="center" gutterBottom>
        Daily Exercise Tracker
      </Typography>
      <Grid container spacing={2}>
        <Grid item xs={15} key={workSheet.id} style={{}}>
          {/* 오늘의 요일만 보이도록 조건부 스타일링을 적용합니다. */}
          <Paper style={{ padding: 16 }}>
            <Grid container spacing={3} alignItems="center">
              <Grid item xs={12}>
                <Box display="flex" gap={2} width={1} alignItems="center" justifyContent="space-between">
                  <TextField
                    label="Enter time (minutes)"
                    type="number"
                    value={exerciseTime}
                    onChange={(event) => setExerciseTime(event.target.value)}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    fullWidth
                  />
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => handleSave()}
                    style={{ height: 56 }}
                  >
                    저장
                  </Button>
                </Box>
              </Grid>
              <Grid item xs={12}>
                <TextField
                  // label="Daily review"
                  placeholder='Enter your daily review here...'
                  value={dailyReview}
                  onChange={(event) => setDailyReview(event.target.value)}
                  fullWidth
                  multiline
                  rows={4}
                  variant="outlined"
                  style={{ marginTop: '8px' }}
                />
              </Grid>
            </Grid>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};


export default WorkoutFeedback;
