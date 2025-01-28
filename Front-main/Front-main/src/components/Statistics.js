import { Box, Container, Tooltip, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { Bar, BarChart, CartesianGrid, Legend, ResponsiveContainer, XAxis, YAxis } from "recharts";
import { DatePicker } from '@mui/x-date-pickers';
import dayjs from "dayjs";
import { call } from "../service/ApiService";

function Statistics({ }) {
    const [chartData, setChartData] = useState([]);

    const [startDate, setStartDate] = useState(dayjs().subtract(7, 'day').format('YYYY-MM-DD'));
    const [endDate, setEndDate] = useState(dayjs().format('YYYY-MM-DD'));

    const [highestValue, sethighestValue] = useState(0);

    useEffect(() => {
        fetchStatistics(startDate, endDate);
    }, [startDate, endDate]);

    const fetchStatistics = (startDate, endDate) => {
        // fetch data from API
        // http://{{API_HOST}}/worksheet/statistics?start=2024-03-10&end=2024-03-17
        const url = `/worksheet/statistics?start=${startDate}&end=${endDate}`;
        call(url, "GET", null)
            .then((response) => {
                const _data = response.data.map((item) => {
                    return {
                        day: item.date,
                        time: Number(item.timeSpent)
                    }
                })
                _data.sort((a, b) => {
                    return a.day.localeCompare(b.day);
                })

                // find highest value
                let highest = 0;
                _data.forEach((item) => {
                    if (item.time > highest) {
                        highest = item.time;
                    }
                });
                sethighestValue(highest);

                setChartData(_data);
            });
    }

    // http://{{API_HOST}}/worksheet/statistics?start=2024-03-10&end=2024-03-17

    const handleBarClick = (event) => {
        console.log(event);
    }

    return (
        <Container>

            <Typography variant="h5" align="center" gutterBottom style={{ marginTop: '32px', marginBottom: '16px' }}>
                Weekly Exercise Statistics
            </Typography>
            <Box display="flex" gap={2} alignItems="center" justifyContent="space-between">
                <DatePicker
                    value={dayjs(startDate)}
                    onChange={(newDate) => {
                        // console.log(newDate)
                        // setDate(newDate)
                        setStartDate(newDate.format('YYYY-MM-DD'))
                    }}
                />

                <DatePicker
                    value={dayjs(endDate)}
                    onChange={(newDate) => {
                        // console.log(newDate)
                        // setDate(newDate)
                        setEndDate(newDate.format('YYYY-MM-DD'))
                    }}
                />
            </Box>
            <div style={{ marginTop: 16 }}></div>
            <ResponsiveContainer width="100%" height={300}>
                <BarChart data={chartData} onClick={handleBarClick}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="day" interval={0} angle={-45} textAnchor="end" height={60} />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="time" fill="#8884d8" name="Exercise Time (minutes)" />
                </BarChart>
            </ResponsiveContainer>

        </Container>
    );
}


export default Statistics;