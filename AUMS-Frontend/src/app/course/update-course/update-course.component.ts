import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AllcoursesService } from '../../services/allcourses.service';
@Component({
  selector: 'app-update-course',
  templateUrl: './update-course.component.html',
  styleUrls: ['./update-course.component.css'],
})
export class UpdateCourseComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<UpdateCourseComponent>,
    private service: AllcoursesService
  ) {}

  dialogformgroup = new FormGroup({
    courseId: new FormControl(this.data.courseId, Validators.required),
    courseName: new FormControl(this.data.courseName, Validators.required),
    courseDescription: new FormControl(
      this.data.courseDescription,
      Validators.required
    ),
    prerequisite: new FormControl(this.data.prerequisite, Validators.required),
    userId: new FormControl(this.data.userId),
  });

  close(): void {
    this.dialogRef.close();
  }

  edit(): void {
    if (this.dialogformgroup.valid) {
      this.service.editCourse(this.dialogformgroup.getRawValue());
    } else {
      this.close();
    }
    this.dialogRef.close();
  }

  ngOnInit(): void {}
}
