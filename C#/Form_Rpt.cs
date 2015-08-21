using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using DSSCOMMasterLib;
using System.Threading;

namespace WindowsFormsApplication1
{
    public partial class Form_Rpt : Form
    {

        public Form_Rpt()
        {
            InitializeComponent();
        }

        //レポートのSQLを表示
        private void button1_Click(object sender, EventArgs e)
        {

            string reportGuid = "796249EE4429B79CD62FC195F59A6764";

            try
            {

                MSTRConn con1 = new MSTRConn();

                IDSSSession oSession = con1.getSession();

                IDSSSource oSource = oSession.ObjectSource;

                IDSSReportSource oReportSource = oSession.ReportSource;

                IDSSReportDefinition oReportDef = (IDSSReportDefinition)oSource.FindObject(reportGuid, EnumDSSObjectType.DssTypeReportDefinition,
                                                           EnumDSSSourceFlags.DssSourceDefault, null, 0, 0);


                IDSSReportInstance oReportInstance = oReportSource.ExecuteDefinition(oReportDef, EnumDSSReportActions.DssActionExecuteSQL, EnumDSSReportFlags.DssReportRunSynch, null, 0, 0);

                //IDSSReportInstance oReportInstance = oReportSource.NewInstance(oReportDef);
                //oReportSource.Execute(oReportInstance, EnumDSSReportActions.DssActionGenerateSQL, EnumDSSReportFlags.DssReportLocalOnly,null,0,0);
                //oReportSource.GenerateSQL(oReportInstance,EnumDSSReportFlags.DssReportCacheDefault,null,0,0);

                int i =0;
                while ((int)(oReportInstance.State & EnumDSSReportStates.DssActionGenerateSQLDone) != 2)
                {
                    Thread.Sleep(100);
                    i++;
                    if (i == 10) break;
                }

                Console.WriteLine(i);

                if (i != 10) { 
                    foreach (IDSSSQL sql in oReportInstance.SQLs)
                    {
                        Console.WriteLine(sql.DisplaySQL);
                    }
                }

                con1.close();

            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: " + ex.Message.ToString());
            }
        }

        //Extended Data Access (XDA) reporting (Freeform SQL or Query Builder).
        private void button2_Click(object sender, EventArgs e)
        {
            string reportGuid = "1ACE8C37484AE0EBD81271BB88B63013";

            try
            {
                MSTRConn con1 = new MSTRConn();

                IDSSSession oSession = con1.getSession();

                IDSSSource oSource = oSession.ObjectSource;

                IDSSReportSource oReportSource = oSession.ReportSource;

                IDSSReportDefinition4 oReportDef = (IDSSReportDefinition4)oSource.FindObject(reportGuid, EnumDSSObjectType.DssTypeReportDefinition,
                                                           EnumDSSSourceFlags.DssSourceDefault, null, 0, 0);


                Console.WriteLine(oReportDef.XDAType);

                IDSSTable3 oTable = (IDSSTable3)oReportDef.SourceTable;

                Console.WriteLine(oTable.XDADefinition);

                con1.close();

            }
            catch (Exception ex)
            {
                Console.WriteLine("Error: " + ex.Message.ToString());
            }

        }



    }
}
