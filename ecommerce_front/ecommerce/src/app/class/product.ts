import { Category } from "./category";

export class Product {
    constructor(
        public id:number,
        public name:string,
        public code:string,
        public description:string,
        public price:number,
        public url_image:string,
        public categories:Category[],
        public Image:File,
    ) {}
}