import { KeywordImageModel } from "./keyword_image";

export type KeywordModel = {
    keyword_name: string;
    images: KeywordImageModel[];
}