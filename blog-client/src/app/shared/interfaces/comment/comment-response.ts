export interface CommentResponse {
  id: number;
  postId: number;
  content: string;
  userId: number;
  createdAt: string;
  updatedAt: string;
  fullName: string;
  profilePicture: string;
}
