import './Messages.css'
import {useEffect, useState} from "react";
import {SentMessage} from "../../models/Message/SentMessage";
import {ReceivedMessage} from "../../models/Message/ReceivedMessage";
import {deleteMessage, fetchReceivedMessages, fetchSentMessages, readMessage} from "../../services/UserService";
import {DefaultButton, IconButton, Modal, Stack, StackItem} from "@fluentui/react";
import {useNavigate} from "react-router-dom";

function Messages() {
    const navigate = useNavigate();

    const [sentMessages, setSentMessages] = useState<SentMessage[]>([]);
    const [receivedMessages, setReceivedMessages] = useState<ReceivedMessage[]>([]);
    const [sent, setSent] = useState<boolean>(false);
    const [page, setPage] = useState<number>(1);
    const [modalOpen, setModalOpen] = useState<boolean>(false);
    
    useEffect(() => {
        (async function() {
            if (sent) {
                const result = await fetchSentMessages(page);
                setSentMessages(result);
            } else {
                const result = await fetchReceivedMessages(page);
                setReceivedMessages(result);
            }
        })();
    }, [page, sent]);

    function _onClickInbox() {
        setSent(false);
        setPage(1);
    }

    function _onClickOutbox() {
        setSent(true);
        setPage(1);
    }

    function _onClickNew() {
        navigate("/messages/new");
    }

    function _onClickView(id: number) {
        if (!sent) {
            readMessage(id).then(r => console.log(r));
        }
        setModalOpen(true);
    }

    function _onClickDelete(id: number) {
        deleteMessage(id).then(r => console.log(r));
        setSent(false);
    }

    return (
        <div>
            <Stack>
                <StackItem className="buttons-container">
                    {sent ?
                        <DefaultButton text="Inbox" onClick={_onClickInbox}/>
                    :
                        <DefaultButton text="Outbox" onClick={_onClickOutbox}/>
                    }
                    <DefaultButton text="New message" onClick={_onClickNew}/>
                </StackItem>
                <StackItem className="messages-list-container">
                    {sent ?
                        sentMessages.map((sentMessage) => (
                            <div className="sent-messages-row" key={sentMessage.id}>
                                <div>ID: {sentMessage.id}</div>
                                <div>To: {sentMessage.recipient}</div>
                                <div>Date: {sentMessage.creationDate}</div>
                                <div>
                                    <DefaultButton text="View" onClick={() => _onClickView(sentMessage.id)}/>
                                    <DefaultButton text="Delete" onClick={() => _onClickDelete(sentMessage.id)}/>
                                </div>
                                <Modal
                                    isOpen={modalOpen}
                                    onDismiss={() => setModalOpen(false)}
                                >
                                    <Stack className="modal-container">
                                        <StackItem className="modal-top-row">
                                            <h2>Message</h2>
                                            <IconButton iconProps={{iconName: "Cancel"}} onClick={() => setModalOpen(false)}/>
                                        </StackItem>
                                        <StackItem>
                                            <div>To: {sentMessage.recipient}</div>
                                            <div>Date: {sentMessage.creationDate}</div>
                                            <div>Title: {sentMessage.title}</div>
                                            <div>Body: {sentMessage.body}</div>
                                        </StackItem>
                                    </Stack>
                                </Modal>
                            </div>
                        ))
                    :
                        receivedMessages.map((receivedMessage) => (
                            <div className="sent-messages-row" key={receivedMessage.id}>
                                <div>ID: {receivedMessage.id}</div>
                                <div>From: {receivedMessage.recipient}</div>
                                <div>Date: {receivedMessage.creationDate}</div>
                                <div>{receivedMessage.read ? "Read" : "New"}</div>
                                <div>
                                    <DefaultButton text="View" onClick={() => _onClickView(receivedMessage.id)}/>
                                </div>
                                <Modal

                                    isOpen={modalOpen}
                                    onDismiss={() => setModalOpen(false)}
                                >
                                    <Stack className="modal-container">
                                        <StackItem className="modal-top-row">
                                            <h2>Message</h2>
                                            <IconButton iconProps={{iconName: "Cancel"}} onClick={() => setModalOpen(false)}/>
                                        </StackItem>
                                        <StackItem>
                                            <div>From: {receivedMessage.sender}</div>
                                            <div>Date: {receivedMessage.creationDate}</div>
                                            <div>Title: {receivedMessage.title}</div>
                                            <div>Body: {receivedMessage.body}</div>
                                        </StackItem>
                                    </Stack>
                                </Modal>
                            </div>
                        ))
                    }
                </StackItem>
            </Stack>
        </div>
    );
}

export default Messages;