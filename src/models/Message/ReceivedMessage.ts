export interface ReceivedMessage {
    id: number;
    title: string;
    body: string;
    read: boolean;
    creationDate: Date;
    sender: string;
    recipient: string;
}