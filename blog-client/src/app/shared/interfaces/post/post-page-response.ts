export interface PostPageResponse {
  id: number;
  title: string;
  thumbnail: string;
  category: string;
  tags: Array<string>;
  createdAt: string;
  updatedAt: string;
}
