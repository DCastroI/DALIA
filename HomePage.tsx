import { on } from "events";

interface HomePageProps{
    onMostrarCreador: () => void;
}
const HomePage: React.FC<HomePageProps> = ({onMostrarCreador}) => {

    const handleClick = () =>{
        onMostrarCreador();
    }
    return(
        <div>
            <h1> Bienvenido a Dalia</h1>
            <button onClick={handleClick}> 
                Crear clip
            </button>
        </div>
    );
}

export default HomePage; 
