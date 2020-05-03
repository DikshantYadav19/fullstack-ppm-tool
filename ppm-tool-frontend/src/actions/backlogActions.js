import axios from 'axios'
import { GET_ERRORS, GET_BACKLOG, GET_PROJECT_TASK, DELETE_PROJECT_TASK } from './types'

export const addProjectTask = (backlog_id, project_task, history) => async dispatch => {

    try {
        await axios.post(`/api/backlog/${backlog_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
};

export const getBacklog = backlog_id => async dispatch => {
    try {
        const res = await axios.get(`/api/backlog/${backlog_id}`);
        dispatch({
            type: GET_BACKLOG,
            payload: res.data
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
};

export const getProjectTask = (backlog_id, pt_id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/backlog/${backlog_id}/${pt_id}`);
        dispatch({
            type: GET_PROJECT_TASK,
            payload: res.data
        })
    } catch (error) {
        history.push("/dashboard");
    }
};

export const updateProjectTask = (backlog_id, pt_id, project_task, history) => async dispatch => {

    try {
        await axios.patch(`/api/backlog/${backlog_id}/${pt_id}`, project_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }
};

export const deleteProjectTask = (backlog_id, pt_id) => async dispatch => {
    if (window.confirm(`You are deleting project task ${pt_id}, this action cannot be undone. Are you sure?`)) {
        try {
            await axios.delete(`/api/backlog/${backlog_id}/${pt_id}`);
            dispatch({
                type: DELETE_PROJECT_TASK,
                payload: pt_id
            });
        } catch (error) {

        }
    }
}