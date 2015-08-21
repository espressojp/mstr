using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DSSCOMMasterLib;

namespace WindowsFormsApplication1
{

    public class MSTRConn
    {
        private const string MSTRServer = "JPN-AHORIUCHI2";
        private const string MSTRLogin = "Administrator";
        private const string MSTRPwd = "";

        private DSSDataSource oServerDS;
        private IDSSSession oSession;


        public MSTRConn()
        {

            oServerDS = new DSSDataSource();
            oServerDS.Type = EnumDSSDataSourceType.DssDataSourceTypeServer;
            oServerDS.Mode = EnumDSSConnectionMode.DssConnectionModeServerAccess;
            oServerDS.AuthMode = EnumDSSAuthModes.DssAuthStandard;
            oServerDS.Location = MSTRServer;
            oServerDS.port = 34952;
            oServerDS.login = MSTRLogin;
            oServerDS.Passwd = MSTRPwd;

            oServerDS.Init();

        }


        public IDSSSession getSession()
        {
            IDSSDataSource oProject = oServerDS.Enumerator.Item(1);　 //ItemByProjectName

            oProject.Init();
            //oSession = oServerDS.CreateSession();

            oSession = oProject.CreateSession();

            return oSession;

        }


        public void close()
        {

            if (oSession != null)
            {
                Console.WriteLine("closing..");
                oServerDS.Reset();
                oServerDS = null;
                oSession.Close();
                oSession = null;
            }


        }
    }
}
