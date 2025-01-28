import React, { useState } from 'react';
import { TextField, Paper, Button, Select, MenuItem } from '@mui/material';
import moment from 'moment';

const routines = [
  { value: 'routine1', label: '가슴' },
  { value: 'routine2', label: '등' },
  { value: 'routine3', label: '하체' },
  { value: 'routine4', label: '어깨' },
  { value: 'routine5', label: '팔' },
  { value: 'routine6', label: '코어' },
  { value: 'routine7', label: '유산소' },
  { value: 'routine8', label: '스트레칭' },
  { value: 'routine9', label: '홈트' },
];

const AddTodo = ({ date, add }) => {
  const [item, setItem] = useState('');
  const [count, setCount] = useState(1);


  const onButtonClick = () => {
    let datas = [];
    const routine = routines.find((r) => r.value === item);
    const title = routine.label;
    for (let i = 0; i < count; i++) {
      datas.push({
        title: count > 1 ? `${title} ${i + 1}세트` : title,
        date: moment(date).format('YYYY-MM-DD'),
      });
    }
    add(datas);

    setItem('');
  };

  // const handleKeyDown = (e) => {
  //   if (e.key === 'Enter') {
  //     onButtonClick();
  //   }
  // };

  return (
    <Paper style={{ padding: 16 }}>
      <div style={{ display: 'flex', alignItems: 'center' }}>
        <Select
          value={item}
          onChange={(e) => {
            e.preventDefault();
            setItem(e.target.value);
          }}
          style={{
            marginRight: 16,
            flexGrow: 1,
            flexShrink: 1,
          }}
        >
          {routines.map((routine) => (
            <MenuItem key={routine.value} value={routine.value}>
              {routine.label}
            </MenuItem>
          ))}
        </Select>
        <TextField
          placeholder='Count'
          type='number'
          onChange={(e) => setCount(e.target.value)}
          value={count}
          style={{
            width: 100,
            marginRight: 16,
            flexGrow: 0,
            flexShrink: 0,
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
          추가
        </Button>
      </div>
    </Paper>
  );
};

export default AddTodo;
