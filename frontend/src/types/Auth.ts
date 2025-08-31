type User = {
    username: string;
    token: string;
}

type Auth = {
    user: User;
    login: Function;
    logout: Function;
}

export { User, Auth };