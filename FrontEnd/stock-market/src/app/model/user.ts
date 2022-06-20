
export interface User {
    id?:number;
    userName:string;
    password:string;
    email:string;
    mobileNumber:string;
    confirmed?:boolean;
}