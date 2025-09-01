import {fetchWithToken} from "./ApiUtils";
import {Product} from "../types/Product";

const getProducts = async (token: string) : Promise<Product[]> => new Promise((resolve, reject) => {
    fetchWithToken('/api/v1/products', token)
        .then(result => {
            if (!result.ok) {
                reject("Failed to fetch products");
            }

            resolve(result.json());
        })
});

export { getProducts };