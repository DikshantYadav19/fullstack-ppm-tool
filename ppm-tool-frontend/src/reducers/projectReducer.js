import { GET_PROJECTS } from "../actions/types"

const initialSate = {
    projects: [],
    project: {}
};

export default function (state = initialSate, action) {
    switch (action.type) {
        case GET_PROJECTS:
            return {
                ...state,
                projects: action.payload
            }
        default:
            return state;
    }
}