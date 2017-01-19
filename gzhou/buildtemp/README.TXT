This installer will apply the Vitria Form Builder 3.2 to your Vitria OI 6.0 installation.


Prerequisites:
* To apply the Vitria Form Builder 3.2, you must have an existing Vitria OI 6.0 installation.
* For OI MySQL database, please check its parameter "max_allowed_packet" is bigger enough to install Form Builder App (around 50MB). For example change it to 100MB. No issue in other database type;
* Please adjust JBoss start script to make sure you have enough memory(-Xmx); The original 512MB is not enough for Form Builder, you can start from 1024MB;

Installation steps:
To apply the Vitria Form Builder 3.2 to an existing Vitria OI 6.0 installation, follow the steps below:
1. Get Vitria Form Builder 3.2 zip file and unzip it, you will get two zip files.
2. Make sure JBoss of Vitria OI 6.0 is stopped.
3. Unzip FormBuilder_Patch_6.0.zip under VTBA_HOME, there are "install.bat" and "install.sh" under VTBA_HOME/FormBuilder_Patch_6.0 folder. For Windows platforms, run install.bat. For Unix platforms, run install.sh.
4. Start JBoss.
5. Install FormBuilder.3.2.zip in App Admin UI.
6. Verify Form Builder related features.
Note: If you use a cluster environment with multiple nodes, please do once for #5 at one node (App/Module installer is stored into central repository, installing once is enough), but #3 need to apply in each node (the patch is for each Vitria OI platform).

Uninstallation steps:
To remove the Vitria Form Builder 3.2 from an existing Vitria OI 6.0 installation, follow the steps below:
1. Make sure JBoss of Vitria OI 6.0 is started.
2. Un-install FormBuilder.3.2.zip in App Admin UI.
3. Stop JBoss.
4. Browse to VTBA_HOME\uninstall\Uninstall_FormBuilder.
   For Windows platforms, run uninstall.bat. For Unix platforms, run uninstall.sh.
   Wait until the uninstallation finishes, and check VTBA_HOME\logs\FormBuilderPatch-uninstall.log to see if the uninstallation completed successfully.
5. Start Vitria OI JBoss. Verify the Form Builder features are removed completely.
