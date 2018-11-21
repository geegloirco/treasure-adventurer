import { BrowserModule } from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import {BaseComponent} from './component/base/base.component';
import {RootModule} from './component/root/root.module';
import {RootContainerService} from './component/root/root-container/root-container.component';
import {ServerInfoService} from './service/server-info/server-info.service';
import {PersonalityService} from './service/personality/personality.service';
import {UserService} from './service/user/user.service';
import {AddressService} from './service/address/address.service';
import {WindowRefService} from './service/window-ref/window-ref.service';
import {MessageService} from './service/message/message.service';
import {RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';

export function startupServiceFactory(personalityService: PersonalityService): Function {
  return () => {
    personalityService.init();
  };
}

@NgModule({
  declarations: [
    BaseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    RootModule
  ],
  providers: [
    RootContainerService,
    ServerInfoService,
    PersonalityService,
    UserService,
    AddressService,
    {
      // Provider for APP_INITIALIZER
      provide: APP_INITIALIZER,
      useFactory: startupServiceFactory,
      deps: [PersonalityService],
      multi: true
    },
    WindowRefService,
    MessageService
  ],
  bootstrap: [BaseComponent]
})
export class AppModule { }
