import React, { useEffect, useState } from 'react';

import axios from 'axios';

const SaveName = () => {

    const [name , setName] = useState('');
    const [message, setMessage] = useState('');
    const [users, setUsers] = useState('');

 const fetchUsers = async (e) =>{

        try{
            const response = await axios.get('http://localhost:8080/react/users');
            setUsers(response.data);
        }catch(error){
            console.log('Error fetching users', error);
            setMessage("Error:Could not load user list");

        }

    };

    useEffect(() =>{
        fetchUsers();

        const interVal = setInterval(() =>{
            fetchUsers();
        },2000);
        return() => clearInterval(interVal);
    },[]);



    const handleSubmit =  async (e) =>{
        e.preventDefault();
        setMessage("Saving...")
        console.log("saving");

        try {

            const response = await axios.post("http://localhost:8080/react/users",{
                name},{ timeout : 5000}
            );

            console.log("User saved", response.data);
            setMessage(`User "${name}" saved successfully`);
            setName('');


        }catch(error){
            console.error("Error saving user", error);
            setMessage('Error : could not save the name');
        }
    };

    return(

        <div>
            <h2>Save user name</h2>
            <form onSubmit={handleSubmit}>
                <input 
                  type ="text"
                  value = {name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder='Enter a name'
                  required/>

                  <button type='submit' disabled={!name}>Save to DB</button>
                  </form>

                  {message && <p>{message}</p>}

                  <h3>Saved Users</h3>
                  {users.length === 0 ?(
                    <p>No user saved yet</p>
                ):(
                    <ul>
                        {users.map((user) => (
                            <li key={user.id}> 
                            {user.name} (ID:{user.id})

                            </li>
                        ))}
                    </ul>
                ) }
        </div>
    );

};

export default SaveName;

