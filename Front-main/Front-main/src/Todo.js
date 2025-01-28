import React, { useState } from 'react';
import {
  ListItem,
  ListItemText,
  InputBase,
  Checkbox,
  ListItemSecondaryAction,
  IconButton,
} from '@mui/material';
import { DeleteOutlined } from '@mui/icons-material';

function Todo({ todoItem, deleteItem, updateItem }) {
  const [item, setItem] = useState(todoItem);
  const [readOnly, setReadOnly] = useState(true);

  const deleteEventHandler = () => {
    deleteItem(item);
  };

  const offReadOnlyMode = () => {
    console.log('Event!', readOnly);
    setReadOnly(false);
  };

  const enterKeyEventHandler = (e) => {
    if (e.key === 'Enter') {
      setReadOnly(true);
      updateItem(item);
    }
  };

  const editEventHandler = (e) => {
    const thisItem = { ...item };
    thisItem.title = e.target.value;
    setItem(thisItem);
  };

  const checkboxEventHandler = (e) => {
    const thisItem = { ...item };
    thisItem.done = !thisItem.done;
    setItem(thisItem);
    updateItem(thisItem);
  };

  return (
    <ListItem>
      <Checkbox checked={item.done} onChange={checkboxEventHandler} />
      <ListItemText>
        <InputBase
          inputProps={{
            'aria-label': 'naked',
            readOnly: readOnly,
          }}
          type='text'
          id={item.id}
          name={item.id}
          value={item.title}
          fullWidth={true}
          onClick={offReadOnlyMode}
          onChange={editEventHandler}
          onKeyDown={enterKeyEventHandler}
        />
      </ListItemText>

      <ListItemSecondaryAction>
        <IconButton aria-label='Delete Todo' onClick={deleteEventHandler}>
          <DeleteOutlined />
        </IconButton>
      </ListItemSecondaryAction>
    </ListItem>
  );
};

export default Todo;
