import { Component, OnInit } from '@angular/core';
import { SocialAuthService } from 'angularx-social-login';
import { GoogleLoginProvider } from 'angularx-social-login';
import { SocialUser } from 'angularx-social-login';
import { Router } from '@angular/router';
import { LoginserviceService } from '../services/loginservice.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  user: any;
  loggedIn!: boolean;

  constructor(
    private authService: SocialAuthService,
    private router: Router,
    private loginservice: LoginserviceService
  ) {}

  ngOnInit() {
    if (!this.loginservice.isLoggedIn()) {
      this.router.navigate(['/']);
    }
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = user != null;
    });
  }

  signIn() {
    this.loginservice.signInWithGoogle();
  }
}
