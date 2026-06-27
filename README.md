how to use program
1.Configure SQL Server

SQL Server must be set to Mixed Mode Authentication and the sa account must be enabled.
how to apply this:
A.Open SSMS and connect using Windows Authentication
B.Right-click the server name in Object Explorer → Properties
C.Go to the Security tab
D.Select SQL Server and Windows Authentication mode → click OK

2.Enable the sa account:

Open a New Query in SSMS and run:
ALTER LOGIN sa ENABLE;
ALTER LOGIN sa WITH PASSWORD = '12345';
GO

3.Enable TCP/IP:

A.Open SQL Server Configuration Manager
B.Go to SQL Server Network Configuration → Protocols for SQLEXPRESS
C.Right-click TCP/IP → Enable

4. Restart SQL Server:
in the command prompt type
net stop MSSQL$SQLEXPRESS
net start MSSQL$SQLEXPRESS

how to play:
1.Launch the app — the connection test runs automatically in the terminal
2.Log in with one of the accounts above
3.Click any cell on the board to place your X
4.The computer plays as O and will try to block or win
5.After the game ends, your stats are saved automatically
6.Check My Statistics or Top 5 Scorers from the main menu

Troubleshooting:
"Login failed for user 'sa'"
→ Follow Setup steps 1A–1D above. Make sure SQL Server was fully restarted after enabling Mixed Mode.

"SQL Server JDBC driver not found"
→ Make sure the .jar file is in lib/ and included in the -cp classpath when compiling and running.

"Cannot connect to localhost\SQLEXPRESS"
→ TCP/IP is likely disabled. Follow step 1C and restart the service.

Blank leaderboard / stats not updating
→ The game_project database may not exist. Re-run schema.sql in SSMS.
