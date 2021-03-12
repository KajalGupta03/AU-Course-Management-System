import { Byte } from '@angular/compiler/src/util';
import { Timestamp } from 'rxjs';

export interface courses {
  courseDescription: string;
  courseId: number;
  courseName: string;
  createdOn: string;
  lastUpdated: string;
  prerequisite: string;
  userId: number;
  imageUrl: string
}

export interface skills {
  skillName: string;
  skillId: number;
}

export interface feedbacks {
  participantName: string;
  feedbackId: number;
  courseId: number;
  feedbackText: string;
  rating: number;
  createdAt: string;
}

export interface material {
  materialId: number;
  courseId: number;
  parentId: number;
  isCurrent: number;
  createdAt: string;
  lastUpdated: string;
  materialDescription: string;
  fileType: string;
  fileData: File;
}

export interface user {
  firstName: string;
  lastName: string;
  emailId: string;
  userId: number;
}

export interface trends{
  name: string;
  value: number;
}
