import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AdminProductComponent } from './components/admin-product/admin-product.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { CategoryListComponent } from './components/category/category-list/category-list.component';
import { CategoryFormComponent } from './components/category/category-form/category-form.component';
import { DetailProductComponent } from './components/cart/detail-product/detail-product.component';
import { SumaryOrderComponent } from './components/order/sumary-order/sumary-order.component';
import { PaymentSuccessComponent } from './components/order/payment-success/payment-success.component';
import { RegisterComponent } from './components/user/register/register.component';
import { LoginComponent } from './components/user/login/login.component';
import { LogoutComponent } from './components/user/logout/logout.component';
import { authGuard } from './guard/auth.guard';

export const routes: Routes = [
    {
        path:"",
        component:HomeComponent
    },
    {
        path:"admin",
        component:AdminProductComponent
    },
    {
        path:"admin/product/:id/form",
        component:ProductFormComponent
    },
    {
        path:"admin/categories",
        component:CategoryListComponent
    },
    {
        path:"admin/category/:id/form",
        component: CategoryFormComponent
    },
    {
        path:'cart/detailProduct',
        component: DetailProductComponent
    },
    {
        path:"cart",
        component: SumaryOrderComponent,
        canActivate: [authGuard]
    },
    {
        path:"payment/success",
        component:PaymentSuccessComponent
    },
    {
        path:"user/register",
        component:RegisterComponent
    },
    {
        path:"user/login",
        component:LoginComponent
    },
    {
        path:"user/logout",
        component:LogoutComponent
    }
];
