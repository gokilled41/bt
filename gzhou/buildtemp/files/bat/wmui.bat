call mudir
cd ..
call ant do-locale

call mudir
cd m3o\webapp\core
call ant

call mudir
cd m3o\webapp\workspace
call ant management
call ant domain

call mudir
cd m3oModule
call ant update-domain
