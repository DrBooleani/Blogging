export interface PostPageResponse {
  content: Array<{
    id: number;
    title: string;
    thumbnail: string;
    category: string;
    tags: Array<string>;
    createdAt: string;
    updatedAt: string;
  }>;
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  };
}