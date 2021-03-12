import { Injectable } from '@angular/core';
import { SocialAuthService } from 'angularx-social-login';
import { GoogleLoginProvider } from 'angularx-social-login';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { user } from './../ApiResponse';

@Injectable({
  providedIn: 'root',
})
export class LoginserviceService {
  constructor(
    private authService: SocialAuthService,
    private router: Router,
    private http: HttpClient
  ) {}

  user: any;

  getUser(): user {
    this.user = sessionStorage.getItem('user');
    console.log(this.user);
    return this.user;
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID).then((x) => {
      console.log(x);
      var user_data = {
        firstName: x.firstName,
        lastName: x.lastName,
        emailId: x.email,
      };

      this.http
        .post<any>('http://localhost:8080/login/googleLogin', user_data)
        .subscribe((data) => {
          console.log(data);
          sessionStorage.setItem('user', JSON.stringify(data));
        });

      this.router.navigateByUrl('/home');
    });
  }

  signOut(): void {
    sessionStorage.removeItem('user');
    this.authService.signOut();
    console.log('user signed out');
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem('user') != null;
  }
}
