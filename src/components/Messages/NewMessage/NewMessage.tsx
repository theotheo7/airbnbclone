import './NewMessage.css'
import {DefaultButton, Stack, StackItem, TextField} from "@fluentui/react";
import React, {useState} from "react";
import {MessagePosting} from "../../../models/Message/MessagePosting";
import {sendMessage} from "../../../services/UserService";
import {useNavigate} from "react-router-dom";

function NewMessage() {
    const navigate = useNavigate();

    const [recipient, setRecipient] = useState<string>("");
    const [title, setTitle] = useState<string>("");
    const [body, setBody] = useState<string>("");

    function _onChangeRecipient(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setRecipient(newValue ? newValue : "");
    }

    function _onChangeTitle(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setTitle(newValue ? newValue : "");
    }

    function _onChangeBody(
        _event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>,
        newValue?: string
    ): void {
        setBody(newValue ? newValue : "");
    }

    async function _onClickSend() {
        try {
            await sendMessage(new MessagePosting(title, body, recipient));
            window.alert("Message sent!");
            navigate("/messages");
        } catch (err) {
            console.log(err);
            window.alert("Something went wrong! Try again.");
        }
    }

    return (
        <div className="new-message-container">
            <h1>New message</h1>
            <Stack className="new-message-box">
                <StackItem>
                    <TextField label="To:" value={recipient} onChange={_onChangeRecipient}/>
                </StackItem>
                <StackItem>
                    <TextField label="Title:" value={title} onChange={_onChangeTitle}/>
                </StackItem>
                <StackItem>
                    <TextField label="Body:" value={body} onChange={_onChangeBody} style={{height: '15vh'}} multiline/>
                </StackItem>
                <StackItem className="send-button">
                    <DefaultButton text="Send" onClick={_onClickSend}/>
                </StackItem>
            </Stack>
        </div>
    );
}

export default NewMessage;