import {fetchWithToken} from "./ApiUtils";
import {Product} from "../types/Product";
import {User} from "../types/Auth";

const ProductApi = () => {
    const user : User = JSON.parse(localStorage.getItem("user"));
    const {token} = user;

    const getProducts = async (): Promise<Product[]> => new Promise((resolve, reject) => {
        fetchWithToken('/api/v1/products', token)
            .then(result => {
                if (!result.ok) {
                    reject("Failed to fetch products");
                }

                resolve(result.json());
            })
    });

    const addProduct = async (name: string, description: string): Promise<Product> =>
        new Promise((resolve, reject) => {
            fetchWithToken('/api/v1/products', token, 'POST', {name, description})
                .then(result => {
                    if (!result.ok) {
                        reject("Failed to add product");
                    }

                    resolve(result.json());
                })
        });

    const deleteProduct = async (id: string): Promise<void> =>
        new Promise((resolve, reject) => {
            fetchWithToken(`/api/v1/products/${id}`, token, 'DELETE')
                .then(result => {
                    if (!result.ok) {
                        reject("Failed to delete product");
                    }
                    resolve();
                })
        });

    return {
        getProducts,
        addProduct,
        deleteProduct,
    }
}

export default ProductApi();