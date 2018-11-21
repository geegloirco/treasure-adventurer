import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RootContainerComponent} from './root-container/root-container.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {RouterModule} from '@angular/router';
import {BrowserModule} from '@angular/platform-browser';
import {MessagesComponent} from './messages/messages.component';
import {UserInfoComponent} from './user-info/user-info.component';
import {NavbarViewComponent} from './navbar-view/navbar-view.component';
import {FooterViewComponent} from './footer-view/footer.component';
import {UserInfoViewComponent} from './user-info-view/user-info-view.component';
import {LoadWaitComponent} from './load-wait/load-wait.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {UserMenuComponent} from "./user-menu/user-menu.component";
import {LoginViewComponent} from "./login-view/login-view.component";
import {HomeViewComponent} from "./home-view/home-view.component";

@NgModule({
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    RouterModule,
    NgbModule
  ],
  declarations: [
    DashboardComponent,
    UserInfoComponent,
    RootContainerComponent,
    MessagesComponent,
    NavbarViewComponent,
    UserMenuComponent,
    LoginViewComponent,
    FooterViewComponent,
    HomeViewComponent,
    UserInfoViewComponent,
    LoadWaitComponent,
  ],
  providers: [
  ],
  exports: [
    DashboardComponent,
    UserInfoComponent,
    NavbarViewComponent,
    UserMenuComponent,
    LoginViewComponent,
    FooterViewComponent,
    HomeViewComponent,
    UserInfoViewComponent,
    LoadWaitComponent,
  ],
  entryComponents: [
  ]
})
export class RootModule { }
