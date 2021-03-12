import { Component, OnInit, ViewChild } from '@angular/core';
import { SocialAuthService } from 'angularx-social-login';
import { Router } from '@angular/router';
// import { listData } from '../../list';
import { ElementRef } from '@angular/core';
import { AllcoursesService } from '../services/allcourses.service';
import { courses } from './../ApiResponse';
import { MatDialog } from '@angular/material/dialog';
import { LoginserviceService } from '../services/loginservice.service';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {




  displayedColumns: string[] = [ 'courseName', 'courseDescription','prerequisite','button1'];
  CourseList = [];
  dataSource:any=new MatTableDataSource<courses>();





  @ViewChild('searchbar')
  searchbar!: ElementRef;
  searchText = '';
  list: any;
  constructor(
    private authService: SocialAuthService,
    private router: Router,
    private AllcoursesService: AllcoursesService,
    public dialog: MatDialog,
    private loginservice: LoginserviceService
  ) {}

  signOut(): void {
    this.authService.signOut();
    console.log('user signed out');
    this.router.navigateByUrl('/');
  }
  // list = listData.reverse();

  toggleSearch: boolean = false;

  openSearch() {
    this.toggleSearch = true;
    this.searchbar.nativeElement.focus();
  }
  searchClose() {
    this.searchText = '';
    this.toggleSearch = false;
  }

  ngOnInit(): void {

    if (!this.loginservice.isLoggedIn()) {
      this.router.navigate(['/']);
    }
    this.AllcoursesService.courses().subscribe((response: courses) => {
      console.log(response);
      this.list = response;
      this.dataSource.data = response;
      console.log(this.dataSource.data);
    });
    ///
    // this.dataSource = new MatTableDataSource(this.list);
    
    console.log(this.dataSource);
    ////
  }

  gotocourse(i: number): void {
    ///this.AllcoursesService.courseid = i;
    this.router.navigate(['/course',i]);
  }

  // openDialog(): void {
  //   let dialogRef = this.dialog.open(HomeDialogComponent, {
  //     width: '350px',
  //     height: '500px',
  //   });

  //   dialogRef.afterClosed().subscribe((result) => {
  //     console.log('The dialog was closed');

  //     console.log(result);
  //     this.fetch();
  //   });
  // }

  async fetch() {
    this.list = [];

    await this.AllcoursesService.courses().subscribe((response: courses) => {
      console.log(response);
      this.list = response;
      // this.router.onSameUrlNavigation = 'reload';
      // this.router.navigateByUrl('/home');
      location.reload();
    });
  }

  delete(i: number): void {
    this.AllcoursesService.courseid = i;
    this.AllcoursesService.deleteCourseById(i).subscribe(
      (response: boolean) => {
        console.log(response);
      }
    );
    this.fetch();
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  addCourse(){
    this.router.navigate(['addCourse']);
  }

  showTrends(){
    this.router.navigate(['trends']);
  }
}
