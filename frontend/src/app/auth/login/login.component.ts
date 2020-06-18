import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginRequestPayload} from './login-request.payload';
import {AuthService} from '../shared/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {throwError} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginRequestPayload: LoginRequestPayload;
  registerSuccessMessage: string;
  isError: boolean;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.loginRequestPayload = {
      username: '',
      password: ''
    };

    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params.registered !== undefined &&
          params.registered === 'true') {
          this.toastr.success('Регистрация прошла успешно!');
          this.registerSuccessMessage = 'Проверьте письмо активации в mailtrap или найдите ссылку в логах для активации аккаунта!';
        }
      });
  }

  login() {
    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;

    this.authService.login(this.loginRequestPayload).subscribe(() => {
      this.isError = false;
      this.router.navigateByUrl('');
      this.toastr.success('Добро пожаловать');
    }, error => {
      this.isError = true;
      throwError(error);
    });
  }

}
