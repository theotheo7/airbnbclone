import {UPDATE_HEADER_TITLE} from "./actionTypes";

export interface UpdateHeaderTitleAction {
    type: typeof UPDATE_HEADER_TITLE;
    payload: string;
}

export const updateHeaderTitle = (title: string): UpdateHeaderTitleAction => {
    return {
        type: UPDATE_HEADER_TITLE,
        payload: title,
    };
};