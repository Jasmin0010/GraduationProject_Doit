import { Container, List, Paper, Typography } from '@mui/material';
import React from 'react';
import AddTodo from './AddTodo';
import Todo from '../Todo';

function TodoSection({ date, add, todoItems, deleteItem, updateItem }) {

    return (
        <Container>
            <AddTodo date={date} add={add} />
            <div className="TodoList">
                <Paper style={{ marginTop: 16 }}>
                    <List>
                        {todoItems ? todoItems.map((item, idx) => (
                            <Todo
                                key={item.id}
                                // item={item}
                                // deleteItem={deleteItem}
                                // update={update}
                                todoItem={item}
                                deleteItem={deleteItem}
                                updateItem={updateItem}
                            />
                        )) : null}
                    </List>
                </Paper>
            </div>
        </Container>
    );
}

export default TodoSection;