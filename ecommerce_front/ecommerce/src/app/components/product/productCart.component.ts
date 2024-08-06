import { CommonModule } from "@angular/common";
import { Component, Input } from "@angular/core";
import { RouterLink } from "@angular/router";

@Component({
    selector: 'app-product',
    standalone: true,
    imports: [RouterLink,CommonModule],
    templateUrl: './productCart.component.html',
    styleUrls: []
})

export class ProductCartComponent {
    @Input() id!:number
    @Input() name!:string
    @Input() price!:number 
    @Input() urlImage!:string
}