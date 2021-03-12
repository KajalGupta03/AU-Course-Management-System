import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LoginserviceService } from '../services/loginservice.service';
import { AllcoursesService } from '../services/allcourses.service';
import { courses } from './../ApiResponse';
import { skills } from './../ApiResponse';
import { feedbacks } from './../ApiResponse';
import { UpdateCourseComponent } from './update-course/update-course.component';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MaterialService } from '../services/material.service';
import { SkillService } from '../services/skill.service';
@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit {
  course: any;
  listskills: any;
  feedbacks: any;
  key: any;
  formbool:boolean[]=[];
  materialObject: any = {};
  openlatest: boolean = false;
  openAll: boolean = false;
  allmaterials: any = [];
  materials: any;
  skills:any;
  formgroup!:FormGroup;
  show:boolean[]=[];
  previousMaterial:any=[];
  

  constructor(
    private AllcoursesService: AllcoursesService,
    public dialog: MatDialog,
    private loginservice: LoginserviceService,
    private router: Router,
    private materialService: MaterialService,
    private skillService: SkillService,
    private activatedRoute:ActivatedRoute
  ) {}

  

  ngOnInit(): void {
    if (!this.loginservice.isLoggedIn()) {
      this.router.navigate(['/']);
    }
    console.log(this.AllcoursesService.courseid)

    this.activatedRoute.paramMap.subscribe(param=>{
      this.AllcoursesService.courseid=param.get("courseId")
    })

    this.AllcoursesService.getCourseById(
      this.AllcoursesService.courseid
    ).subscribe((response: courses) => {
      // console.log(response);
      this.course = response;
      console.log(this.course);

      this.AllcoursesService.getSkillByCourseId(
        this.AllcoursesService.courseid
      ).subscribe((response: skills) => {
        console.log(response);
        this.listskills = response;
      });
  
      this.AllcoursesService.getFeedbackByCourseId(
        this.AllcoursesService.courseid
      ).subscribe((response: feedbacks) => {
        console.log(response);
        this.feedbacks = response;
      });
  
      this.skillService.getSkillInCourse(
        this.AllcoursesService.courseid
      ).subscribe((response: any) => {
        console.log(response);
        this.skills = response;
      });
  
      // this.getlatest();
     
      this.materialService
        .getLatestMaterialByCourseId(this.AllcoursesService.courseid)
        .subscribe((result) => {
          console.log(result.materialDescription)
          this.materials = result;
          console.log(this.materials);
          this.formgroup = new FormGroup({
            materialDescription: new FormControl(this.materials.materialDescription, Validators.required),
            fileType: new FormControl(this.materials.fileType, Validators.required),
            file: new FormControl('', Validators.required),
          });
        });  
    });

    
   
  }

  edit(coursedummy: any): void {
    
    let dialogRef = this.dialog.open(UpdateCourseComponent, {
      
      data: {
        courseId: coursedummy.courseId,
        courseName: coursedummy.courseName,
        courseDescription: coursedummy.courseDescription,
        prerequisite: coursedummy.prerequisite,
        userId: coursedummy.userId,
      },
    });

    this.fetch();
  }

  async fetch() {
    this.key = this.course.courseId;

    this.course = [];
    this.AllcoursesService.getCourseById(this.key).subscribe((result) => {
      this.course = result;
    });
  }

  updatematerial(key:any, index:any): void {
    //this.formbool[index]=true;
    //this.openAll=false;
    //this.openlatest=false;
    this.formbool[index] = false;
    this.materialObject.courseId = this.AllcoursesService.courseid;
    this.materialObject.fileType = this.formgroup.controls['fileType'].value;
    this.materialObject.file = this.formgroup.controls['file'].value._files;
    this.materialObject.materialDescription = this.formgroup.controls['materialDescription'].value;
    this.materialObject.previousMaterialId = key;
    this.materialService
      .updateByMaterialId(this.materialObject)
      .subscribe((response2) => {
        console.log(response2);
      });
    //this.show[index] = false;
  }

getlatest() {
    this.openlatest = true;
    this.openAll = false;
    for (var val of this.formbool) {
      val = false;
    }
    this.materialService
      .getLatestMaterialByCourseId(this.AllcoursesService.courseid)
      .subscribe((result) => {
        this.materials = result;
        console.log(this.materials);
      });
  }

  // getAll() {
  //   this.openlatest = false;
  //   this.openAll = true;
  //   this.materialService
  //     .getAllMaterial(this.AllcoursesService.courseid)
  //     .subscribe((result) => {
  //       this.allmaterials = result;
  //       console.log(this.allmaterials);
  //     });
  //     // this.formbool=false;
  // }


  disable() {
    this.openlatest = false;
    this.openAll = false;
  }


  showPrevious(key:any,index:any){
    this.materialService
      .showPrevious(this.AllcoursesService.courseid, key)
      .subscribe((result) => {
        this.materials[index].previousMaterial = result;
        console.log(this.previousMaterial);
      });
      this.show[index]=true;
    
  }
  hidePrevious(index:any){
    this.show[index]=false;
    console.log(this.show);
  }

}
