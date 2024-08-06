export class User {
    constructor(
        public id:number | null,
        public username:string,
        public firstname:string,
        public lastname:string,
        public email:string,
        public address:string,
        public phone:string,
        public password:string,
        public userType:userType
    ) {}
}

export class UserCredentials {
    constructor(
        public email:string,
        public password:string
    ) {}
}
export enum userType {
    ADMIN = 'ADMIM',
    USER = 'USER'
}