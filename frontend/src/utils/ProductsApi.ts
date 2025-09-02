import {fetchWithToken} from "./ApiUtils";
import {Product} from "../types/Product";

const getProducts = async (token: string): Promise<Product[]> => new Promise((resolve, reject) => {
    fetchWithToken('/api/v1/products', token)
        .then(result => {
            if (!result.ok) {
                reject("Failed to fetch products");
            }

            resolve(result.json());
        })
});

const addProduct = async (token: string, name: string, description: string): Promise<Product> =>
    new Promise((resolve, reject) => {
        fetchWithToken('/api/v1/products', token, 'POST', {name, description})
            .then(result => {
                if (!result.ok) {
                    reject("Failed to add product");
                }

                resolve(result.json());
            })
    });

const deleteProduct = async (token: string, id: string): Promise<void> =>
    new Promise((resolve, reject) => {
        fetchWithToken(`/api/v1/products/${id}`, token, 'DELETE')
            .then(result => {
                if (!result.ok) {
                    reject("Failed to delete product");
                }
                resolve();
            })
    });

export {getProducts, addProduct, deleteProduct};