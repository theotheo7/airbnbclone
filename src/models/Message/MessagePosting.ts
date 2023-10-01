interface IMessagePosting {
    title: string;
    body: string;
    recipient: string;
}

export class MessagePosting implements IMessagePosting {
    title: string;
    body: string;
    recipient: string;

    constructor(title: string, body: string, recipient: string) {
        this.title = title;
        this.body = body;
        this.recipient = recipient;
    }
}