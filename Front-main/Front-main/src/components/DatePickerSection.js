import React, { useState } from 'react';
import { Box, TextField, Typography } from '@mui/material';
import moment from 'moment';
import { DatePicker } from '@mui/x-date-pickers';
import dayjs from 'dayjs';


const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

function DatePickerSection({ date, setDate }) {
    const today = moment(date).weekday() // 오늘의 요일을 구합니다.

    return (
            <div style={{ padding: 16 }}>

                <Box display="flex" gap={2} alignItems="center" justifyContent="space-between">

                    <Typography variant="h6" gutterBottom>{daysOfWeek[today]}</Typography>
                    <DatePicker
                        value={dayjs(date)}
                        onChange={(newDate) => {
                            // console.log(newDate)
                            // setDate(newDate)
                            setDate(newDate.format('YYYY-MM-DD'))
                        }}
                    />
                </Box>
            </div>
    );
}

export default DatePickerSection;