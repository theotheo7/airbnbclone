import {UpdateHeaderTitleAction} from "../actions";
import {UPDATE_HEADER_TITLE} from "../actions/actionTypes";

interface RootState {
    headerTitle: string;
}

const initialState: RootState = {
    headerTitle: 'My App',
};

type RootAction = UpdateHeaderTitleAction;

const rootReducer = (state = initialState, action: RootAction): RootState => {
    switch (action.type) {
        case UPDATE_HEADER_TITLE:
            return {
                ...state,
                headerTitle: action.payload,
            };
        default:
            return state;
    }
};

export default rootReducer;