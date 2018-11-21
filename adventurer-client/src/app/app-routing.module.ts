import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {RootContainerComponent} from './component/root/root-container/root-container.component';
import {DashboardComponent} from './component/root/dashboard/dashboard.component';
import {UserInfoComponent} from './component/root/user-info/user-info.component';
import {UserMenuComponent} from "./component/root/user-menu/user-menu.component";
import {LoginViewComponent} from "./component/root/login-view/login-view.component";
import {HomeViewComponent} from "./component/root/home-view/home-view.component";

const routes: Routes = [
  { path: '', redirectTo: '/root/(rootContainer:dashboard/(dashboardBody:login))', pathMatch: 'full' },
  { path: 'root', component: RootContainerComponent, children: [
      { path: 'dashboard', component: DashboardComponent, outlet: 'rootContainer', children: [
          { path: 'login', component: LoginViewComponent, outlet: 'dashboardBody'},
          { path: 'home', component: HomeViewComponent, outlet: 'dashboardBody'}
        ]
      },
    ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
